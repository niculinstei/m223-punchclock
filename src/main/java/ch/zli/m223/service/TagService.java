package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Tag;

@ApplicationScoped
public class TagService {
    
    @Inject
    private EntityManager entityManager;

    public List<Tag> findAll() {
        var query = entityManager.createQuery("FROM Tag", Tag.class);
        return query.getResultList();
    }

    @Transactional
    public Tag addTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Transactional
    public void delete(long id) {
        var tagToDelete = entityManager.find(Tag.class, id);
        entityManager.remove(tagToDelete);
    }
}
