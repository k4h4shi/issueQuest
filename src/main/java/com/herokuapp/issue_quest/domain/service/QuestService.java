package com.herokuapp.issue_quest.domain.service;

import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herokuapp.issue_quest.domain.model.Quest;
import com.herokuapp.issue_quest.domain.model.QuestStatus;
import com.herokuapp.issue_quest.domain.repository.QuestRepository;

/**
 * クエストのサービス
 * 
 * @author k4h4shi
 *
 */
@Service
public class QuestService {

  @Autowired
  QuestRepository questRepository;

  /**
   * 全てのクエストを取得する
   * 
   * @return 全てのクエスト
   */
  @Transactional(readOnly = true)
  public List<Quest> findAllQuests() {
    List<Quest> allQuests = questRepository.findAll();
    allQuests.sort(createQuestComparator());
    return allQuests;
  }

  /**
   * クエストを一件作成する
   * 
   * @param title クエストのタイトル
   * @param description クエストの説明
   * @return 作成されたクエスト
   */
  @Transactional()
  public Quest createQuest(String title, String description) {
    Quest quest = new Quest(title, description);
    return questRepository.save(quest);
  }

  /**
   * クエストの状態を更新する
   * 
   * @param questId クエストのId
   * @param status クエストの状態
   */
  @Transactional()
  public void updateQuestStatus(String questId, QuestStatus status) {
    if (!questRepository.exists(questId)) {
      // TODO update対象が存在しない場合の例外を送出する
      return;
    }

    Quest target = questRepository.findOne(questId);
    if (target.getStatus().equals(status)) {
      // TODO 更新対象による変更がない場合の例外を送出する
      return;
    }
    target.setStatus(status);
    
    questRepository.flush();
  }
  
  /**
   * クエストのIdで比較するコンパレータを生成する
   * @return クエストのIDで比較するコンパレータ
   */
  private Comparator<Quest> createQuestComparator() {
    return new Comparator<Quest>() {

      @Override
      public int compare(Quest q1, Quest q2) {
        return q1.getId().compareTo(q2.getId());
      }};
  }
}
