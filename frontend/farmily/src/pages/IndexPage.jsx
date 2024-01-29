import { Link } from 'react-router-dom';

import mainTree from '../assets/images/mainTree.png';
import board from '../assets/images/mainboard.png';

const welcomeMessage = 'Welcome Farmily';
const introTitle_1 = '가족과 함께';
const introTitle_2 = '나무를 키워보세요!';
const introContent_1 = '가족과의 추억을 기록하고';
const introContent_2 = '열매를 맺어 수확하세요.';

export default function IndexPage() {
  return (
    <>
      <div className="w-2/6 mb-60 pl-36">
        <p className="text-3xl font-bold mb-4">{welcomeMessage}</p>
        <p className=" text-2xl text-">{introTitle_1}</p>
        <p className="text-2xl">{introTitle_2}</p>
        <br />
        <p className="">{introContent_1}</p>
        <p className="">{introContent_2}</p>
        <br />
        <Link to="login">
          <button className=" bg-gray-700 text-white text-xl px-6 py-4 rounded-xl">
            로그인
          </button>
        </Link>
      </div>
      <img className="w-3/6 mb-28" src={mainTree} alt="" />
      <div className="w-1/6 mr-32 mb-28 relative">
        <p className="text-white text-4xl absolute top-14 left-8">시작하기</p>
        <img src={board} alt="" />
      </div>
    </>
  );
}
