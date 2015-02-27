package com.craigburke.document.core

/**
 * Table row node
 * @author Craig Burke
 */
class Row extends BaseNode {
	List<Cell> children = []
	Integer position = 0
    Integer width
}
