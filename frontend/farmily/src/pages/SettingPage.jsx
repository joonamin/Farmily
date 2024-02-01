import { Link } from 'react-router-dom';

export default function SettingPage() {
  return (
    <>
      <h1>설정 페이지</h1>
      <Link to="/createtree">가족 생성하기</Link>
    </>
  );
}
