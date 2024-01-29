export default function EventCard(item) {
  return (
    <div className="h-full p-5 snap-x w-1/4 flex-shrink-0">
      <div className="w-full h-full bg-white shadow-md border border-gray-200 rounded-lg max-w-sm dark:bg-gray-800 dark:border-gray-700 p-3">
        <div className="w-full object-cover">
          <img
            className="h-full rounded-lg m-auto"
            src={item.image}
            alt="tree"
          />
        </div>
        <div className="h-1/4 w-full">
          <h3 className="h-full text-lg mt-2">{item.content}</h3>
        </div>
      </div>
    </div>
  );
}
