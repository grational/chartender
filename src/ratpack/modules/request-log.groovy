/*
 * This ratpack file was auto generated by 'gigawatt'
 * @author gsus
 * @date 03-04-2017 14.56
 */
import static ratpack.groovy.Groovy.ratpack
import ratpack.handling.RequestLogger

ratpack {
	handlers {
		// all(RequestLogger.ncsa())
		all(RequestLogger.of { outcome ->
			// Only log when logger is enabled.
			if (RequestLogger.LOGGER.infoEnabled) {
				// Log how long the request handling took.
				RequestLogger.LOGGER.info(
					'Request for {} took {}.{} seconds.',
					outcome.request.uri,
					outcome.duration.seconds,
					(outcome.duration.nano / 1_000_000)
				)
		  }
	  })
	}
}
// vim: ft=groovy:fdm=indent
