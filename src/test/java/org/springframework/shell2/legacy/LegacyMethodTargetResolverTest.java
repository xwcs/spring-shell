package org.springframework.shell2.legacy;

import static org.junit.Assert.assertThat;

import java.util.Map;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell2.MethodTarget;
import org.springframework.shell2.MethodTargetResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ericbottard on 09/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LegacyMethodTargetResolverTest.Config.class)
public class LegacyMethodTargetResolverTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private LegacyCommands legacyCommands;

	@Autowired
	private MethodTargetResolver resolver;

	@Test
	public void findsMethodsAnnotatedWithCliCommand() throws Exception {
		Map<String, MethodTarget> targets = resolver.resolve(applicationContext);

		assertThat(targets, IsMapContaining.hasEntry(
				"register module",
				new MethodTarget(LegacyCommands.REGISTER_METHOD, legacyCommands, "Register a new module" )
		));
	}

	@Configuration
	static class Config {

		@Bean
		public LegacyCommands legacyCommands() {
			return new LegacyCommands();
		}

		@Bean
		public MethodTargetResolver methodTargetResolver() {
			return new LegacyMethodTargetResolver();
		}
	}

}
