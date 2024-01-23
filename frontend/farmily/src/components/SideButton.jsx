import { Link } from 'react-router-dom';

export default function SideButton(category) {
  return (
    <Link to={category.url}>
      <button className="px-5 py-2 text-lg rounded-md hover:bg-gray-200">
        {category.name}
        {/* 버튼 */}
      </button>
    </Link>
  )
}