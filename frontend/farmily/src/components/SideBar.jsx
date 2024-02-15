import logout from '../assets/images/logout.png';
import SideButton from './SideButton.jsx';
import logo from '../assets/images/Farmily.png';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { logOut } from '../store/auth.jsx';
import { useNavigate } from 'react-router-dom';

export default function SideBar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const family = useSelector((state) => state.family.value);
  const user = useSelector((state) => state.user.value);
  const [familyName, setFamilyName] = useState('가족');
  const [familyImage, setFamilyImage] = useState('');
  const [category, setCategory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [nickname, setNickname] = useState('');
  const [showFull, setShowFull] = useState(false);

  const handleMouseEnter = () => {
    setShowFull(true);
  };

  const handleMouseLeave = () => {
    setShowFull(false);
  };
  useEffect(() => {
    setFamilyName(family.name);
    setFamilyImage(family.profileDto.location);
    setNickname(user.nickname);

    setCategory([
      {
        name: '📑 기록하기',
        url: `/family/record/${family?.mainSprint.sprintId}`,
        category: 'record',
      },
      { name: '🌳 추억보기', url: '/family/memory', category: 'memory' },
      { name: '📅 일정보기', url: '/family/calendar', category: 'calendar' },
      { name: '🖼 커뮤니티', url: '/family/community', category: 'community' },
      {
        name: '🏆 업적보기',
        url: '/family/achievement',
        category: 'achievement',
      },
      { name: '💬 소통하기', url: '/family/contact', category: 'contact' },
    ]);
  }, [family]);

  useEffect(() => {
    setLoading(false);
  }, [familyImage]);

  function clickLogout() {
    // 로그아웃 요청 보내기
    dispatch(logOut());
    navigate('/');
  }
  return (
    <aside className="w-1/3 h-full px-4 py-6 bg-slate-50 text-stone-900 md:w-60 rounded-r-md text-center z-50">
      {/* 파밀리 로고 */}
      <Link to={`/tree/${family.id}`}>
        <img src={logo} alt="logo" className="size-32 mx-auto mb-4" />
      </Link>

      {/* 가족 프로필 사진 */}
      {loading ? (
        <div className="size-40 m-auto">
          <div
            className="inline-block h-8 w-8 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite] mt-12"
            role="status"
          >
            <span className="!absolute !-m-px !h-px !w-px !overflow-hidden !whitespace-nowrap !border-0 !p-0 ![clip:rect(0,0,0,0)]">
              Loading...
            </span>
          </div>
        </div>
      ) : (
        <img src={familyImage} alt="family-profile" className="size-40 mx-auto object-cover" />
      )}
      <div className="flex justify-center mt-2 overflow-hidden">
        <div
          className={`h-8 px-2 bg-gray-300 rounded-2xl flex items-center transition-all duration-50 ${
            showFull ? 'w-full' : 'w-8'
          }`}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        >
          <p className="whitespace-nowrap text-center w-full">
            {showFull ? <>{nickname}님</> : <>{nickname?.charAt(0)}</>}
          </p>
        </div>
      </div>
      {/* 가족이름 */}
      <h2 className="mx-auto mt-2 font-bold text-xl text-stone-900">
        {familyName}
        <Link to="/family/setting" className="text-lg align-middle">
          {' '}
          ⚙️
        </Link>
      </h2>

      <div className="mb-2">
        {/* 카테고리 */}
        {/* url 정의 후 수정 필요 */}
        <ul>
          {category.map((categoryItem, categoryIndex) => (
            <SideButton
              key={categoryIndex}
              category={categoryItem.category}
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
