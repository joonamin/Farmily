import { useState, useEffect } from 'react';

export default function CommonModal({
  title,
  content,
  isOpen,
  closeModal,
  onClickHandler,
}) {
  return (
    <>
      {isOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center overflow-x-hidden overflow-y-auto outline-none focus:outline-none">
          <div className="relative w-1/5 mx-auto my-6">
            {/* 모달 콘텐츠 */}
            <div className="relative flex flex-col w-full bg-white border-0 rounded-lg shadow-lg outline-none focus:outline-none">
              {/* 헤더 */}
              <div className="flex items-start justify-between p-5 border-b border-solid border-gray-300 rounded-t">
                <h3 className="text-3xl font-semibold">{title}</h3>
                <button
                  className="p-1 ml-auto bg-transparent border-0 text-black opacity-5 float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
                  onClick={closeModal}
                >
                  <span className="text-black h-6 w-6 text-2xl block outline-none focus:outline-none">
                    X
                  </span>
                </button>
              </div>
              {/* 본문 */}
              <div className="relative p-6 flex-auto">
                {/* 내용을 원하는 대로 추가하세요 */}
                <p>{content}</p>
              </div>
              {/* 푸터 */}
              <div className="flex items-center justify-end p-6 border-t border-solid border-gray-300 rounded-b">
                <button
                  className="text-white bg-blue-500 hover:bg-blue-600 border border-blue-500 rounded-md p-2 mx-2"
                  onClick={() => {
                    closeModal();
                    // onClickHandler(); navigate 이동시 사용
                  }}
                >
                  확인
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
