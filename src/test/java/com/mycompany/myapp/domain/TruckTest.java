package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TruckTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Truck.class);
        Truck truck1 = new Truck();
        truck1.setId(1L);
        Truck truck2 = new Truck();
        truck2.setId(truck1.getId());
        assertThat(truck1).isEqualTo(truck2);
        truck2.setId(2L);
        assertThat(truck1).isNotEqualTo(truck2);
        truck1.setId(null);
        assertThat(truck1).isNotEqualTo(truck2);
    }
}
