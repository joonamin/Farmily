export default function AchievementItem(achievement) {
  return(
    <tr className="h-8 snap-center">
      <td className="w-1/2 text-left pl-4">▪ {achievement.task.content}</td>
      <td className="w-1/12">{achievement.task.percent}%</td>
      <td className="w-1/3">
      <div className="w-full bg-gray-200 rounded-full h-4 dark:bg-gray-700">
        <div className="bg-green-400 h-4 rounded-full dark:bg-green-400 w-10" style={{ width: `${achievement.task.percent}%` }}></div>
      </div>
      </td>
      {/* 데이터 받아오면 버튼 event 추가 */}
      <td className="w-1/12">
        {(achievement.task.percent === 100 && achievement.task.reward === false) ? <button className="text-gray-900 bg-white border-2 border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-1 py-1 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">보상받기</button> : null}
        {(achievement.task.percent === 100 && achievement.task.reward === true) ? <button className="text-sm">받기완료</button> : null}
      </td>
    </tr>
  )
}