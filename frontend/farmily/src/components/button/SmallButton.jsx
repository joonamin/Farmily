import { Link } from 'react-router-dom';

// SmallButton 사용 시 url, text로 버튼 정보 담기
export default function SmallButton(btn) {
  return (
    <Link to={btn.url}>
      <button className="bg-gray-300 px-4 py-2 rounded-md m-4 hover:bg-gray-400">{btn.text}</button>
    </Link>
  );
}
