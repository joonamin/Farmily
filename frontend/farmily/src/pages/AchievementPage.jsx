import { useState, useEffect } from 'react';
import AchievementList from '../components/common/AchievementList.jsx';

// 테스트 data // 데이터 받아오면 삭제
const TESTITEMS = [
  { content: '도전과제 101개 하기', percent: 90, reward: false },
  { content: '도전과제 102개 하기', percent: 0, reward: false },
  { content: '도전과제 102개 하기', percent: 0, reward: false },
  { content: '도전과제 14개 하기', percent: 0, reward: false },
  { content: '도전과제 105개 하기', percent: 0, reward: false },
  { content: '도전과제 110개 하기', percent: 100, reward: false },
  { content: '도전과제 16개 하기', percent: 100, reward: true },
  { content: '도전과제 16개 하기', percent: 100, reward: true },
  { content: '도전과제 110개 하기', percent: 100, reward: false },
  { content: '도전과제 110개 하기', percent: 100, reward: false },
  { content: '도전과제 106개 하기', percent: 22, reward: false },
  { content: '도전과제 106개 하기', percent: 70, reward: false },
  { content: '도전과제 1012개 하기', percent: 50, reward: false },
  { content: '도전과제 140개 하기', percent: 40, reward: false },
  { content: '도전과제 130개 하기', percent: 30, reward: false },
];

export default function AchievementPage() {
  const beforeTasks = filteredByPercent(0, 0);
  const ongoingTasks = filteredByPercent(1, 99);
  const finishedTasks = filteredByPercent(100, 100);

  return (
    <>
      <AchievementList title="시작 전 업적" tasks={beforeTasks} />
      <AchievementList title="진행 중 업적" tasks={ongoingTasks} />
      <AchievementList title="완료 업적" tasks={finishedTasks} />
    </>
  );
}

const filteredByPercent = (minPercent, maxPercent) => {
  return TESTITEMS.filter(
    (item) => item.percent >= minPercent && item.percent <= maxPercent
  );
};
