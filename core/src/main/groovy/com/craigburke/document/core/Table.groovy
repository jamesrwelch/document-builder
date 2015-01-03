package com.craigburke.document.core

class Table {
	def item
	Font font
	List<Row> rows = []
	def parent
	
	Integer columns = 1
	BigDecimal width = (72 * 6)
	BigDecimal borderSize = 1
}