import chunsik from '../../assets/images/chunsik.jpg';

export default function EventCard(item) {
  const imageURL = `${item.image.location}`;
  return (
    <div className="h-full p-5 snap-x w-1/4 flex-shrink-0">
      <div className="w-full h-full bg-white shadow-md border border-gray-200 rounded-lg max-w-sm dark:bg-gray-800 dark:border-gray-700 p-3">
        <div className="w-full h-4/6">
          <img
            className="h-full rounded-lg m-auto object-contain"
            src={imageURL}
            alt={item.image.originalFileName}
          />
        </div>
        <div className="h-1/4 w-full">
          <h3 className="h-full text-md mt-2">{item.description}</h3>
        </div>
      </div>
    </div>
  );
}
