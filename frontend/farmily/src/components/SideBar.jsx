import chunsik from '../assets/images/chunsik.jpg';
import logout from '../assets/images/logout.png';
import SideButton from './SideButton.jsx';
import logo from '../assets/images/Farmily.png';
import { Link } from 'react-router-dom';

import { useDispatch } from 'react-redux';
import { logOut } from '../store/auth.jsx';
import { useNavigate } from 'react-router-dom';

// record => 현재 sprintId 받아오면 수정하기
const sprintId = 1;
const category = [
  { name: '📑 기록하기', url: `/family/record/${sprintId}` },
  { name: '🌳 추억보기', url: '/family/memory' },
  { name: '📅 일정보기', url: '/family/calendar' },
  { name: '🖼 커뮤니티', url: '/family/community' },
  { name: '🏆 업적보기', url: '/family/achievement' },
  { name: '💬 소통하기', url: '/family/contact' },
];

export default function SideBar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  function clickLogout() {
    // 로그아웃 요청 보내기
    dispatch(logOut());
    navigate('/');
  }
  return (
    <aside className="w-1/3 h-full px-4 py-6 bg-slate-50 text-stone-900 md:w-60 rounded-r-md text-center">
      {/* 파밀리 로고 */}
      <Link to="/tree">
        <img src={logo} alt="logo" className="size-32 mx-auto mb-4" />
      </Link>

      {/* 가족 프로필 사진 */}
      <img src={chunsik} alt="family-profile" className="size-40 mx-auto" />

      {/* 가족이름 */}
      <h2 className="mx-auto my-4 font-semibold text-xl text-stone-900">
        춘식라이언 가족
        <Link to="/family/setting" className="text-lg align-middle">
          {' '}
          ⚙️
        </Link>
      </h2>

      <div className="mb-6">
        {/* 카테고리 */}
        {/* url 정의 후 수정 필요 */}
        <ul>
          {category.map((categoryItem, categoryIndex) => (
            <SideButton
              key={categoryIndex}
              name={categoryItem.name}
              url={categoryItem.url}
            />
          ))}
        </ul>
      </div>
      <button onClick={clickLogout}>
        <img className="mx-auto" src={logout} alt="" />
      </button>
    </aside>
  );
}
