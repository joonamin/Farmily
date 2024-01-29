import AchievementItem from './AchievementItem.jsx';

export default function AchievementList(achievement) {
  return (
    <div className="h-1/3 p-10 overflow-hidden">
      <h3 className="text-xl text-left">{achievement.title}</h3>
      <hr className="border-2 border-black" />
      <div className="h-full snap-y overflow-y-scroll">
        <table className="w-full">
          <tbody>
            {achievement.tasks.map((task, index) => (
              <AchievementItem key={index} task={task} />
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
