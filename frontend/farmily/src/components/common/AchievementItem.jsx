import { useSelector } from 'react-redux';
import axios from '../../api/axios.jsx';

export default function AchievementItem({ task }) {
  const family = useSelector((state) => state.family.value);
  const handleClick = () => {
    axios
      .post('/achievement/getReward', {
        familyId: family.id,
        achievement: task.achievement,
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  return (
    <tr className="h-8 snap-center">
      <td className="w-1/2 text-left pl-4">▪ {task.content}</td>
      <td className="w-1/12">{task.percent}%</td>
      <td className="w-1/3">
        <div className="w-full bg-gray-200 rounded-full h-4 dark:bg-gray-700">
          <div
            className="bg-green-400 h-4 rounded-full dark:bg-green-400 w-10"
            style={{ width: `${task.percent}%` }}
          ></div>
        </div>
      </td>

      <td className="w-1/12">
        {task.percent === 100 && task.rewarded === false ? (
          <button
            onClick={handleClick}
            className="text-gray-900 bg-white border-2 border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-1 py-1 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
          >
            보상받기
          </button>
        ) : null}
        {task.percent === 100 && task.rewarded === true ? (
          <button className="text-sm">받기완료</button>
        ) : null}
      </td>
    </tr>
  );
}
