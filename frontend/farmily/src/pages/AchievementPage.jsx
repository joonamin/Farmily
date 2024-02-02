import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import AchievementList from '../components/common/AchievementList.jsx';
import axios from '../api/axios.jsx';

export default function AchievementPage() {
  const [achievement, setAchievement] = useState([
    {
      content: '',
      percent: 0,
      rewardPoint: 0,
      progress: 0,
      rewarded: true,
    },
  ]);
  const family = useSelector((state) => state.family.value);
  useEffect(() => {
    axios
      .get(`/family/${family.id}/achievement`)
      .then((response) => {
        setAchievement(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const beforeTasks = filteredByPercent(achievement, 0, 0);
  const ongoingTasks = filteredByPercent(achievement, 1, 99);
  const finishedTasks = filteredByPercent(achievement, 100, 100);
  return (
    <>
      <AchievementList title="시작 전 업적" tasks={beforeTasks} />
      <AchievementList title="진행 중 업적" tasks={ongoingTasks} />
      <AchievementList title="완료 업적" tasks={finishedTasks} />
    </>
  );
}

const filteredByPercent = (task, minPercent, maxPercent) => {
  return task.filter(
    (item) => item.percent >= minPercent && item.percent <= maxPercent
  );
};
