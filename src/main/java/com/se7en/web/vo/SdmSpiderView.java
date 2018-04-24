package com.se7en.web.vo;

import java.util.LinkedList;
import java.util.List;

public class SdmSpiderView {
	
	private String code;
	
	private String site;
	
	private List<Invitation> invitations = new LinkedList<Invitation>();
	

	@Override
	public String toString() {
		return "SdmSpiderView [code=" + code + ", invitations=" + invitations
				+ "]";
	}

	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * 帖子
	 * @author Administrator
	 *
	 */
	public class Invitation{
		
		private Integer hitCount;
		
		private Integer replayCount;
		
		private String title;
		
		private String authorName;
		
		private String createDate;

		public Integer getHitCount() {
			return hitCount;
		}

		public void setHitCount(Integer hitCount) {
			this.hitCount = hitCount;
		}

		public Integer getReplayCount() {
			return replayCount;
		}

		public void setReplayCount(Integer replayCount) {
			this.replayCount = replayCount;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthorName() {
			return authorName;
		}

		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		@Override
		public String toString() {
			return "Invitation [hitCount=" + hitCount + ", replayCount="
					+ replayCount + ", title=" + title + ", authorName="
					+ authorName + ", createDate=" + createDate + "]";
		}
		
	}
	
}
