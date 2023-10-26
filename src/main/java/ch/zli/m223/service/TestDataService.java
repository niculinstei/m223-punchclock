package ch.zli.m223.service;

import java.time.LocalDateTime;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Entry;
import ch.zli.m223.model.Tag;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;

@IfBuildProfile("dev")
@ApplicationScoped
public class TestDataService {
    
    @Inject EntityManager entityManager;

    @Transactional
    public void createExampleData(@Observes StartupEvent event) {

        //Tags
        Tag tag = new Tag();
        tag.setTitle("BigTime");
        entityManager.persist(tag);

        Tag tag2 = new Tag();
        tag2.setTitle("LowTime");
        entityManager.persist(tag2);
                
        Tag tag3 = new Tag();
        tag3.setTitle("Lol");
        entityManager.persist(tag3);

        //Categories
        Category category1 = new Category();
        category1.setTitle("ProjectA");
        entityManager.persist(category1);

        Category category2 = new Category();
        category2.setTitle("ProjectB");
        entityManager.persist(category2);

        Category category3 = new Category();
        category3.setTitle("ProjectC");
        entityManager.persist(category3);

        //Entries
        Entry entry1 = new Entry();
        entry1.setCategory(category1);
        entry1.setCheckIn(LocalDateTime.of(2023, 3, 2, 1, 0, 0));
        entry1.setCheckOut(LocalDateTime.of(2024, 3, 2, 1, 0, 0));
        entry1.setTags(Set.of(tag, tag2));
        entityManager.persist(entry1);



        
    }
}
