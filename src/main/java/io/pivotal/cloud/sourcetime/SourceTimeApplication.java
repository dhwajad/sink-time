package io.pivotal.cloud.sourcetime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@PropertySource("classpath:/source-time.properties")
@EnableConfigurationProperties(SourceTimeProperties.class)
@EnableBinding(Source.class)
public class SourceTimeApplication {

	// Timestamp configuration properties.
	private final SourceTimeProperties props;

	/**
	 * Automatically called by Spring (no need for <tt>@Autowired</tt>).
	 *
	 * @param props
	 *            Timestamp configuration properties.
	 */
	public SourceTimeApplication(SourceTimeProperties props) {
		this.props = props;
	}

	/**
	 * As this is an InboundAdapter is it generating messages to send to its
	 * channel.
	 *
	 * @return Messages to send to its channel. Every time it is invoked, it
	 *         generates a new message.
	 */
	@Bean
	@InboundChannelAdapter(channel = Source.OUTPUT)
	//@SendTo(Source.OUTPUT)
	public MessageSource<String> sourceTime() {
		return () -> new GenericMessage<>(new SimpleDateFormat(props.getFormat(), props.getLocaleFromString()).format(new Date()));
	}

	public static void main(String[] args) {
		SpringApplication.run(SourceTimeApplication.class, args);
	}

}
