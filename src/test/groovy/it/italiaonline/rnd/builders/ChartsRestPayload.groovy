package it.italiaonline.rnd.builders

import it.italiaonline.rnd.charts.Size
import it.italiaonline.rnd.charts.Labels
import it.italiaonline.rnd.charts.SupportedCharts

import static groovy.json.JsonOutput.toJson

class ChartsRestPayload {
	private final String          title
	private final SupportedCharts type
	private final Size            chartSize
	private final Labels          labels
	private final Boolean         legend
	private final List            dataSources

	/**
	 * Secondary constructor
	 */
	ChartsRestPayload (
		SupportedCharts typ,
		Size            sz,
		List            dss
	) {
		this (
			typ,
			sz,
			dss,
			false
		)
	}

	ChartsRestPayload (
		SupportedCharts typ,
		Size            sz,
		List            dss,
		Boolean         lgn
	) {
		this (
			'',
			typ,
			sz,
			new Labels('',''),
			lgn,
			dss
		)
	}

	/**
	 * Primary constructor
	 */
	ChartsRestPayload (
		String          ttl,
		SupportedCharts typ,
		Size            sz,
		Labels          lbs,
		Boolean         lgnd,
		List            dss
	) {
		this.title = Objects.requireNonNull(ttl)
		this.type = Objects.requireNonNull(typ)
		this.chartSize = Objects.requireNonNull(sz)
		this.labels = Objects.requireNonNull(lbs)
		this.legend = Objects.requireNonNull(lgnd)
		this.dataSources = Objects.requireNonNull(dss)
	}

	String json() {
		toJson(
			[
				title: this.title,
				type: this.type.name().toLowerCase(),
				legend: this.legend,
				dataSources: this.dataSources*.map()
			]
			+
			this.chartSize.map()
			+
			this.labels.map()
		)
	}
}
