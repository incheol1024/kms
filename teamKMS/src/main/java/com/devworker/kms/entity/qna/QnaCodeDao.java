package com.devworker.kms.entity.qna;

import javax.persistence.*;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.entity.common.BoardDao;

@Entity
@Table(name = "KMS_QNA_CODE")
@IdClass(QnaCodeDaoId.class)
public class QnaCodeDao {

/*	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long temp;
*/
	@Id
	@ManyToOne
	@JoinColumn(name = "menu_id", referencedColumnName = "menu_id")
	private MenuDao menuId;

	@Id 
	@ManyToOne
	@JoinColumn(name = "board_id", referencedColumnName = "board_id")
	private BoardDao boardId;

	public MenuDao getMenuId() {
		return menuId;
	}

	public void setMenuId(MenuDao menuId) {
		this.menuId = menuId;
	}

	public BoardDao getBoardId() {
		return boardId;
	}

	public void setBoardId(BoardDao boardId) {
		this.boardId = boardId;
	}

}
