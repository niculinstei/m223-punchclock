package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Entry createEntry(Entry entry) {
        if (this.isCheckinIsBeforeCheckout(entry)) {
            entityManager.persist(entry);
            return entry;
        }
        throw new IllegalArgumentException("Invalid date: Checkin is after Checkout.");
    }

    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry", Entry.class);
        return query.getResultList();
    }

    @Transactional
    public void delete(long id) {
        var entryToDelete = entityManager.find(Entry.class, id);
        entityManager.remove(entryToDelete);
    }

    @Transactional
    public Entry update(long id, Entry entry) {
        if (this.isCheckinIsBeforeCheckout(entry)) {
            entry.setId(id);
            return entityManager.merge(entry);
        }
        throw new IllegalArgumentException("Invalid date: Checkin is after Checkout.");

    }

    private boolean isCheckinIsBeforeCheckout(Entry entry) {
        return entry.getCheckIn().isBefore(entry.getCheckOut());
    }

}
