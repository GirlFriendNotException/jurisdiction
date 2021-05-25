package com.yuzb.model.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description: 分页封装
 **/
public class PageBean<T> implements Serializable {
	private static final long serialVersionUID = -1L;
	private int pageSize;
	private int pageNo;
	/**
	 * 总记录数
	 */
	private int total;

	private List<T> rows = Collections.emptyList(); // 本页的数据列表


	public PageBean(int pageNo, int pageSize, int total, List<T> rows) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.total = total;
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 将页数和每页条目数转换为开始位置和结束位置<br>
	 * 此方法用于不包括结束位置的分页方法<br>
	 * 例如：<br>
	 * 页码：1，每页10 -> [0, 10]<br>
	 * 页码：2，每页10 -> [10, 20]<br>
	 * 。。。<br>
	 *
	 * @param pageNo
	 *            页码（从1计数）
	 * @param countPerPage
	 *            每页条目数
	 * @return 第一个数为开始位置，第二个数为结束位置
	 */
	public static int[] transToStartEnd(int pageNo, int countPerPage) {
		if (pageNo < 1) {
			pageNo = 1;
		}

		if (countPerPage < 1) {
			countPerPage = 0;
		}

		int start = (pageNo - 1) * countPerPage;
		int end = start + countPerPage;

		return new int[] { start, end };
	}

	/**
	 * 根据总数计算总页数
	 *
	 * @param totalCount
	 *            总数
	 * @param numPerPage
	 *            每页数
	 * @return 总页数
	 */
	public static int totalPage(int totalCount, int numPerPage) {
		if (numPerPage == 0) {
			return 0;
		}
		return totalCount % numPerPage == 0 ? (totalCount / numPerPage)
				: (totalCount / numPerPage + 1);
	}
}
