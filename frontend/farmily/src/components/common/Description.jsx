import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import sapling from '../../assets/images/sapling.png';
import mainTree from '../../assets/images/maintree.png';
import family from '../../assets/images/family.png';
import back from '../../assets/images/back.png';
import familyTree from '../../assets/images/familyTree.png';
import description1 from '../../assets/images/description1.jpg';
import description2 from '../../assets/images/description2.jpg';
import intro1 from '../../assets/images/intro1.png';
import intro2 from '../../assets/images/intro2.png';
import intro3 from '../../assets/images/intro3.png';
import intro4 from '../../assets/images/intro4.png';

export default function Description({ isOpen, closeModal }) {
  const [number, setNumber] = useState(1);

  const nextPage = () => {
    setNumber(number + 1);
    console.log(number);
  };

  const prevPage = () => {
    setNumber(number - 1);
    console.log(number);
  };

  return (
    <>
      {isOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center overflow-x-hidden overflow-y-auto outline-none focus:outline-none">
          <div className="relative w-8/12 mx-auto">
            {/* 모달 콘텐츠 */}
            <div className="relative flex flex-col w-full bg-white border-0 rounded-lg shadow-lg outline-none focus:outline-none">
              {/* 헤더 */}
              <div className="flex items-start justify-between p-5 border-b border-solid border-gray-300 rounded-t">
                <h3 className="text-3xl font-semibold">Farmily 소개</h3>
                <span
                  className="text-black h-6 w-6 text-2xl block outline-none focus:outline-none hover:cursor-pointer"
                  onClick={closeModal}
                >
                  X
                </span>
              </div>
              {/* 본문 */}
              <div className="relative p-6">
                {/* 내용을 원하는 대로 추가하세요 */}
                {/* 본문 1 */}
                {number === 4 ? (
                  <div className="relative p-6 px-20 flex justify-between">
                    <div>
                      <p className="mx-auto text-3xl pb-2">Farmily와 함께</p>
                      <p className="mx-auto text-3xl ">
                        가족과 더욱 가까워지세요.
                      </p>
                      <br />
                      <p className="mx-auto text-5xl mt-16 pb-2">함께하는,</p>
                      <p className="mx-auto text-5xl">보다 행복한 삶</p>
                    </div>
                    <div>
                      <img src={family} alt="" className="h-80 mx-auto" />
                    </div>
                  </div>
                ) : null}
                {/* 본문 1 */}
                {/* 본문 2 */}
                {number === 2 ? (
                  <div>
                    <div className="relative p-6 px-20 flex justify-between pt-2">
                      <div>
                        <img src={sapling} alt="" className="h-80 mx-auto" />
                      </div>
                      <div className="flex h-full mt-32">
                        <img src={back} alt="" className=" -scale-x-100 h-16" />
                      </div>
                      <div>
                        <img src={mainTree} alt="" className="h-80 mx-auto" />
                      </div>
                      <div className="flex h-full mt-32">
                        <img src={back} alt="" className=" -scale-x-100 h-16" />
                      </div>
                      <div>
                        <img src={familyTree} alt="" className="h-80 mx-auto" />
                      </div>
                    </div>
                    <div className="flex justify-center">
                      <p>
                        가족 나무를 만들고 추억을 작성해서 열매를 수확하세요!
                      </p>
                    </div>
                  </div>
                ) : null}
                {/* 본문 2 */}
                {/* 본문 3 */}
                {number === 3 ? (
                  <div>
                    <div className="relative p-6 flex justify-between px-40 pt-2">
                      <div>
                        <img
                          src={description1}
                          alt=""
                          className="h-80 mx-auto"
                        />
                      </div>
                      <div>
                        <img
                          src={description2}
                          alt=""
                          className="h-80 mx-auto"
                        />
                      </div>
                    </div>
                    <div className="flex justify-center">
                      <p>추억을 쌓고 포인트를 모아 다양한 과일을 획득하세요!</p>
                    </div>
                  </div>
                ) : null}
                {/* 본문 3 */}
                {/* 본문 4 */}
                {/* 이미지 실서비스 사진 캡쳐해서 바꿀것 */}
                {number === 1 ? (
                  <div>
                    <div className="relative p-6 flex justify-between px-20 pt-14 pb-12">
                      <div className="w-4/6 flex flex-wrap gap-x-2 gap-y-4">
                        <img src={intro1} alt="" className="h-32 w-52" />
                        <img src={intro2} alt="" className="h-32 w-52" />
                        <img src={intro3} alt="" className="h-32 w-52" />
                        <img src={intro4} alt="" className="h-32 w-52" />
                      </div>
                      <div className="text-center my-auto ml-12 w-1/2 flex-col flex text-nowrap">
                        <p className="text-xl">가족의 소중한 순간을</p>
                        <p className="text-xl mt-2">함께 기록하는 공간</p>
                        <p className="text-xl mt-2">
                          우리의 행복한 이야기를 담아요.
                        </p>
                      </div>
                    </div>
                    {/* <div className="flex  justify-center">
                      <p>farmily로 가족들의 추억을 저장하세요!</p>
                    </div> */}
                  </div>
                ) : null}
                {/* 본문 4 */}
              </div>
              {/* 푸터 */}
              <div className="flex items-center justify-between p-6 border-t border-solid border-gray-300 rounded-b">
                <button
                  className={`text-3xl ${
                    number === 1 ? 'opacity-50 cursor-not-allowed' : ''
                  }`}
                  onClick={number === 1 ? null : prevPage}
                >
                  {'<'}
                </button>
                {number === 4 ? (
                  <Link to="login">
                    <button
                      className="text-white bg-gray-700 hover:bg-gray-600 borde rounded-md p-2 mx-2"
                      onClick={() => {
                        closeModal();
                      }}
                    >
                      시작하기
                    </button>
                  </Link>
                ) : null}
                <button
                  className={`text-3xl ${
                    number === 4 ? 'opacity-50 cursor-not-allowed' : ''
                  }`}
                  onClick={number === 4 ? null : nextPage}
                >
                  {'>'}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
