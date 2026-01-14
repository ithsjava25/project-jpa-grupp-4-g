package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.example.service.BootstrapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BootstrapServiceTest {

    private EntityManager em;
    private EntityTransaction tx;
    private TypedQuery<Long> query;
    private BootstrapService service;

    /**
     * Förbereder testmiljön innan varje test.
     * <p>
     * Skapar mockade EntityManager, EntityTransaction och TypedQuery.
     * Initierar BootstrapService med den mockade EntityManager.
     * Detta gör att testerna kan köras utan riktig databas.
     */
    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class);
        tx = mock(EntityTransaction.class);
        query = mock(TypedQuery.class);

        when(em.getTransaction()).thenReturn(tx);

        service = new BootstrapService(em);
    }

    /**
     * Testar att initialize inte gör något om databasen redan är seedad.
     * <p>
     * Verifierar att ingen transaction startas och att ingen SQL körs.
     */
    @Test
    void initialize_databaseAlreadySeeded_doesNotRunSql() {
        when(em.createQuery("select count(c) from Continent c", Long.class)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1L);

        service.initialize();

        verify(tx, never()).begin();

        verify(em, never()).createNativeQuery(anyString());
    }

    /**
     * Testar att initialize gör rollback om dataBaseAlreadySeeded kastar ett undantag.
     * <p>
     * Verifierar att transaction startas, rollback sker och att ett RuntimeException kastas.
     */
    @Test
    void initialize_databaseQueryThrows_rollsBack() {
        when(em.createQuery("select count(c) from Continent c", Long.class))
            .thenThrow(new RuntimeException("table missing"));


        when(tx.isActive()).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, service::initialize);

        verify(tx).begin();
        verify(tx).isActive();
        verify(tx).rollback();

        assertNotNull(ex);
        assertTrue(ex.getMessage().contains("failed to bootstrap database") ||
            ex.getMessage().contains("File not found"));
    }

    /**
     * Testar att initialize försöker köra SQL när databasen inte är seedad,
     * men kastar ett undantag eftersom filen saknas.
     * <p>
     * Verifierar att transaction startas, rollback sker och att ett RuntimeException kastas.
     */
    @Test
    void initialize_databaseNotSeeded_runsSql_throwsBecauseNoFile() {
        when(em.createQuery("select count(c) from Continent c", Long.class))
            .thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);

        when(tx.isActive()).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, service::initialize);

        verify(tx).begin();
        verify(tx).isActive();
        verify(tx).rollback();

        assertNotNull(ex);
        assertTrue(ex.getMessage().contains("File not found") ||
            ex.getMessage().contains("failed to bootstrap database"));
    }
}
