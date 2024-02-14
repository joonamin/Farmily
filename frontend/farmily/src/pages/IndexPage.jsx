import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import TextTypingAni from '../components/TextTypingAni.jsx';
import Description from '../components/common/Description.jsx';

import mainTree from '../assets/images/maintree.png';
import board from '../assets/images/mainboard.png';

const welcomeMessage = 'Welcome Farmily';
const introTitle_1 = '가족과 함께';
const introTitle_2 = '나무를 키워보세요!';
const introContent_1 = '가족과의 추억을 기록하고';
const introContent_2 = '열매를 맺어 수확하세요.';

export default function IndexPage() {
  const [isOpen, setIsOpen] = useState(false);

  const openModal = () => {
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  return (
    <>
      <div className="w-2/6 mb-60 pl-36">
        <TextTypingAni text={welcomeMessage} font={'text-3xl'} />
        <TextTypingAni text={introTitle_1} font={'text-2xl'} />
        <TextTypingAni text={introTitle_2} font={'text-2xl'} />
        <br />
        <TextTypingAni text={introContent_1} />
        <TextTypingAni text={introContent_2} />
        <br />
        <Link to="login">
          <button className="bg-gray-700 text-white text-xl px-6 py-4 rounded-xl">로그인</button>
        </Link>
      </div>
      <div className="w-5/12">
        <img className="mb-28" src={mainTree} alt="" />
      </div>
      <div className="w-1/6 mr-32 mb-28 relative hover:cursor-pointer" onClick={openModal}>
        <p className="text-white text-4xl absolute top-14 left-12 mt-3">알아보기</p>
        <img src={board} alt="" />
      </div>
      <Description isOpen={isOpen} closeModal={closeModal} />
    </>
  );
}
