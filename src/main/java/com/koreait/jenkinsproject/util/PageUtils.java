package com.koreait.jenkinsproject.util;

public class PageUtils {
	
	private int totalRecord;			// DB에서 전체 게시글 갯수 (DB에서 가져옴)
	private int recordPerPage = 10;		// 한 페이지에 표시하는 게시글 갯수 (여기서 정함)
	private int totalPage;				// 전체 페이지 갯수 (totalRecord, recordPerPage로 계산)
	
	/****************************************************
	 	- 전체 10개 게시글
	 	- 한 페이지당 3개 게시글
	 		page = 1, beginRecord = 1, endRecord = 3
	 		page = 2, beginRecord = 4, endRecord = 6
	 		page = 3, beginRecord = 7, endRecord = 9
	 		page = 4, beginRecord = 10, endRecord = 11
	 ******************************************************/

	private int page;					// 현재 페이지 번호 (파라미터로 받아 옴)
	private int beginRecord;			// 각 페이지에 표시되는 시작 게시글 번호 (page, recordPerPage로 계산)
	private int endRecord;				// 각 페이지에 표시되는 종료 게시글 번호 (beginRecord, recordPerPage, totalRecord로 계산)
	
	/**********************************************************************
		- 전체 12 페이지
		- 한 블록당 5개 페이지
		1 block <1 2 3 4 5>,  page = 1 ~5, beginPage = 1, endPage = 5
		2 block <6 7 8 9 10>, page = 6 ~10, beginPage = 6, endPage = 10
		3 block <  11 12  >,  page = 11 ~15, beginPage = 11, endPage = 12	
	 **********************************************************************/
	private int pagePerBlock = 3;		// 한 블록에 표시하는 페이지 갯수(여기서 정함)
	private int beginPage;				// 각 블록에 표시하는 시작 페이지 번호(page, pagePerBlock로 계산)
	private int endPage;				// 각 블록에 표시하는 종료 페이지 번호(beginPage, pagePerBlock, totalPage로 계산)
	
	// void는 반환 타입이 없음 // 여기서 모든 계산을 한다.
	// 페이지 계산을 해야 함
	public void	setPageEntity(int totalRecord, int page) { // 외부에서 totalRecord,page를 받아 옴
		
		this.totalRecord = totalRecord;
		this.page = page;
		
		// 전체 페이지 갯수
		// 토탈 페이지가 50개 이면 레코드 페이지가 10 이니까
		// 페이지 갯수는 5개가 나옴
		// 근데 만약 
		totalPage = totalRecord / recordPerPage;
		if(totalRecord % recordPerPage != 0) {
			totalPage++;
		}
	
		// beginRecord, endRecord
		beginRecord = (page - 1) * recordPerPage + 1;
		endRecord = beginRecord + recordPerPage - 1;
		endRecord = (totalRecord < endRecord) ? totalRecord : endRecord;
	
		// beginPage, endPage
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock;
		endPage = (totalPage < endPage) ? totalPage : endPage;
		
	}
	
	public String getPageEntity(String path) {
		StringBuilder sb = new StringBuilder();
		
		// path에 ? 가 포함되어 있으면 path에 파라미터가 포함되어 있다는 의미임.
		// 예)
		// path = "find.notice?column=WRITER&query=admin"
		// 위와 같은 경우 page 파라미터는 "&page로 path에 추가해야 함"
		// path = "find.notice?column=WRITER&query=admin&page="
		
		
		// 1페이지로 이동 : 1페이지는 링크가 필요 없음
		if(page == 1) {
			sb.append("◀◀&nbsp;");
		}else {
			if(path.contains("?")) {
				sb.append("<a href=\""+ path +"&page=1\">◀◀</a>&nbsp;");
			}else {
				sb.append("<a href=\""+ path +"?page=1\">◀◀</a>&nbsp;");
			}
		}
		// 이전 블록으로 이동 : 1블록은 링크가 필요 없음.
		
		if(page <= pagePerBlock) {
			sb.append("◀&nbsp;");
		}else {
			if(path.contains("?")) {	
				sb.append("<a href=\""+ path +"&page=" + (beginPage - 1) + "\">◀</a>&nbsp;");
			}else {
				sb.append("<a href=\""+ path +"?page=" + (beginPage - 1) + "\">◀</a>&nbsp;");				
			}
		}
		// 페이지 번호 : 현제 페이지는 링크가 필요 없음
		for(int i = beginPage; i <= endPage; i++) {
			if(page == i) {
				sb.append(i + "&nbsp;");
			}else {
				if(path.contains("?")) {
					sb.append("<a href=\""+ path +"&page=" + i + "\">" + i + "</a>&nbsp;");					
				}else {
					sb.append("<a href=\""+ path +"?page=" + i + "\">" + i + "</a>&nbsp;");					
				}
			}
		}
		// 다음 블록으로 이동 : 마지막 블록은 링크가 필요 없음.
		if(endPage == totalPage) {
			sb.append("▶&nbsp;");
		}else {
			if(path.contains("?")) {
				sb.append("<a href=\""+ path +"&page="+ (endPage + 1) +"\">▶</a>&nbsp;");
			}else {
				sb.append("<a href=\""+ path +"?page="+ (endPage + 1) +"\">▶</a>&nbsp;");				
			}
		}
		// 마지막 페이지로 이동 : 마지막 페이지는 링크가 필요 없음.
		if(page == totalPage) {
			sb.append("▶▶&nbsp;");
		}else {
			if(path.contains("?")) {
				sb.append("<a href=\""+ path +"&page=" + totalPage + "\">▶▶</a>&nbsp;");
			}else {
				sb.append("<a href=\""+ path +"?page=" + totalPage + "\">▶▶</a>&nbsp;");				
			}
		}
		
		return sb.toString();
		
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getRecordPerPage() {
		return recordPerPage;
	}
	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getBeginRecord() {
		return beginRecord;
	}
	public void setBeginRecord(int beginRecord) {
		this.beginRecord = beginRecord;
	}
	public int getEndRecord() {
		return endRecord;
	}
	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}
	public int getPagePerBlock() {
		return pagePerBlock;
	}
	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}
