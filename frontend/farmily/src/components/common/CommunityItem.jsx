import { Link } from 'react-router-dom';

export default function CommunityItem(community) {
  return (
    <div className="p-5 snap-center h-80 w-60">
      <Link to={`${community.id}`}>
        <div className="w-full h-full bg-white shadow-md border border-gray-200 rounded-lg max-w-sm dark:bg-gray-800 dark:border-gray-700 p-3">
          <img className="rounded-lg h-4/5 w-full object-contain" src={community.image.location} alt="image" />
          <h3 className="text-lg mt-2 truncate">{community.title}</h3>
          <p className="truncate">작성자 : {community.author}</p>
        </div>
      </Link>
    </div>
  );
}
