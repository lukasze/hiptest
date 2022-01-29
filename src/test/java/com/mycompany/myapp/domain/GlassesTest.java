package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GlassesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Glasses.class);
        Glasses glasses1 = new Glasses();
        glasses1.setId(1L);
        Glasses glasses2 = new Glasses();
        glasses2.setId(glasses1.getId());
        assertThat(glasses1).isEqualTo(glasses2);
        glasses2.setId(2L);
        assertThat(glasses1).isNotEqualTo(glasses2);
        glasses1.setId(null);
        assertThat(glasses1).isNotEqualTo(glasses2);
    }
}
