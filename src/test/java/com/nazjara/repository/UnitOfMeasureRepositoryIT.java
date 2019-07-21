package com.nazjara.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    @DirtiesContext
    public void testFindByDescription1() {
        assertEquals("Teaspoon", unitOfMeasureRepository.findByDescription("Teaspoon").get().getDescription());
    }

    @Test
    public void testFindByDescription2() {
        assertEquals("Cup", unitOfMeasureRepository.findByDescription("Cup").get().getDescription());
    }
}