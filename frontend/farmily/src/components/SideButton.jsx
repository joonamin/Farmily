import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

export default function SideButton({ category, url, name }) {
  const location = useLocation();
  const isActive = location.pathname.includes(category);

  return (
    <Link to={url}>
      <button className={`px-5 py-2 text-lg rounded-md hover:bg-gray-200 ${isActive ? 'bg-gray-200' : ''}`}>
        {name}
        {/* 버튼 */}
      </button>
    </Link>
  );
}
