import chunsik from '../assets/images/chunsik.jpg';
import SideButton from './SideButton.jsx';
import logo from '../assets/images/Farmily.png';
import { Link } from 'react-router-dom';

const category = [
  {name: '📑 기록하기', url: '/tree/record'},
  {name: '🌳 추억보기', url: '/tree/memory'}, 
  {name: '📅 일정보기', url: '/tree/calendar'},  
  {name: '🖼 커뮤니티', url: '/tree/community'},  
  {name: '🏆 업적보기', url: '/tree/achievement'},  
  {name: '💬 소통하기', url: '/tree/contact'},
];

export default function SideBar() {
  return (
    <aside className="w-1/3 h-full px-8 py-6 bg-slate-50 text-stone-900 md:w-60 rounded-r-md text-center font-['DungGeunMo']">
      
      {/* 파밀리 로고 */}
      <Link to="/tree">
        <img src={logo} alt="logo" className=" size-32 mx-auto mb-4" />
      </Link>
      
      {/* 가족 프로필 사진 */}
      <img src={chunsik} alt="family-profile" className="size-40 mx-auto" />

      {/* 가족이름 */}
      <h2 className="mx-auto my-4 font-bold text-xl text-stone-900">춘식라이언 가족</h2>
      
      <div>
        {/* 카테고리 */}
        {/* url 정의 후 수정 필요 */}
        <ul>
            {category.map((categoryItem, categoryIndex) => (
              <SideButton key={categoryIndex} name={categoryItem.name} url={categoryItem.url} />
            ))}
        </ul>
      </div>
      <ul>

      </ul>
    </aside>
  )
}