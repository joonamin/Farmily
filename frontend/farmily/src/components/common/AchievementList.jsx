import AchievementItem from './AchievementItem.jsx';

export default function AchievementList({ title, tasks, handleChange }) {
  return (
    <div className="h-1/3 p-10 overflow-hidden">
      <h3 className="text-xl text-left">{title}</h3>
      <hr className="border-2 border-black" />
      <div className="h-full snap-y overflow-y-scroll">
        <table className="w-full">
          <tbody>
            {tasks.map((task, index) => (
              <AchievementItem
                key={index}
                task={task}
                handleChange={handleChange}
              />
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
