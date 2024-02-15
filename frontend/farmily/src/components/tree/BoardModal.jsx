import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import SmallButton from '../button/SmallButton';
import Draw from './Draw';
import Collection from './Collection';
import Fruits from './Fruits';
import { useParams } from 'react-router-dom';
import { setFamily } from '../../store/family';
import axios from '../../api/axios.jsx';

const BoardModal = ({ isOpen, closeModal, handleChange, familyPoint }) => {
  const dispatch = useDispatch();
  const [selectedTab, setSelectedTab] = useState('segment1');
  const family = useSelector((state) => state.family.value);
  const familyId = family.id;
  const [isRaffling, setIsRaffling] = useState(false);
  const [rafflingResponse, setRafflingResponse] = useState(null);
  const itemSkinsLength = family.fruitSkins ? family.fruitSkins.length : 0;

  const handleOutsideClick = (e) => {
    if (e.target.id === 'my-modal') {
      closeModal();
    }
  };

  const handleTabClick = (tab) => {
    setSelectedTab(tab);
  };

  //  뽑기 로직
  const handleRaffle = async () => {
    // if(point < 100) return;
    setIsRaffling(true); // 뽑기 시작

    try {
      await axios.post('/family/raffling', { familyId }).then((response) => {
        handleChange();
        setRafflingResponse(response.data);
      });
    } catch (error) {
      console.error('Raffle error:', error);
    }

    setTimeout(() => {
      setIsRaffling(false);
    }, 6000);
  };

  const disableOpenBoxButton = isRaffling || familyPoint < 100 || itemSkinsLength >= 21;
  useEffect(() => {
    // Event listener for clicking outside the modal
    const handleDocumentClick = (e) => {
      if (!isOpen || e.target.closest('#my-modal')) {
        return; // Don't close if modal is not open or if click is inside modal
      }
      closeModal();
    };

    // Add event listener when the modal is open
    if (isOpen) {
      document.addEventListener('click', handleDocumentClick);
    }

    // Remove event listener when the modal is closed or unmounted
    return () => {
      document.removeEventListener('click', handleDocumentClick);
    };
  }, [isOpen, closeModal]);

  useEffect(() => {
    setIsRaffling(false);
  }, [isOpen, selectedTab]);

  // useEffect(() => {
  //   setFamilyPoint(family.point)
  // }, [family])

  return (
    <>
      {isOpen && (
        <div
          className="fixed inset-0 flex items-center justify-center z-50 ml-60 py-20"
          id="my-modal"
          onClick={handleOutsideClick}
        >
          <div className="relative p-5 mx-auto bg-white border rounded-md shadow-lg w-96 h-full flex flex-col">
            <div className="bg-gray-200 rounded-lg mb-5" style={{ padding: '2px' }}>
              <div className="relative flex items-center">
                {/* Tab dividers */}
                <div className="absolute w-full">
                  <div className="flex justify-between w-1/3 m-auto">
                    {['segment1', 'segment2', 'segment3'].map((segment) => (
                      <div
                        key={segment}
                        className="w-px h-3 transition-opacity duration-100 ease-in-out bg-gray-400 rounded-full"
                        style={{
                          opacity: selectedTab === segment ? 1 : 0.4,
                        }}
                      ></div>
                    ))}
                  </div>
                </div>

                <div
                  className={`absolute left-0 inset-y-0 w-1/3 flex bg-white transition-all ease-in-out duration-200 transform rounded-md shadow`}
                  style={{
                    transform: `translateX(${
                      selectedTab === 'segment1' ? '0' : selectedTab === 'segment2' ? '100%' : '200%'
                    })`,
                  }}
                ></div>

                {/* Clickable buttons */}
                {['segment1', 'segment2', 'segment3'].map((segment) => (
                  <div
                    key={segment}
                    className={`relative flex-1 flex text-sm font-semibold capitalize items-center justify-center cursor-pointer m-px p-px ${
                      selectedTab === segment ? 'bg-gray-200' : ''
                    }`}
                    onClick={() => handleTabClick(segment)}
                    style={{
                      opacity: selectedTab === segment ? 1 : 0.5,
                    }}
                  >
                    {segment === 'segment1' ? '열매' : segment === 'segment2' ? '도감' : '뽑기'}
                  </div>
                ))}
              </div>
            </div>

            {/* Content based on selected tab */}
            {selectedTab === 'segment1' && (
              <div className="overflow-auto max-h-[calc(100vh-10rem)]">
                <Fruits />
              </div>
            )}
            {selectedTab === 'segment2' && (
              <div className="overflow-auto max-h-[calc(100vh-10rem)]">
                <Collection />
              </div>
            )}
            {selectedTab === 'segment3' && (
              <div className="overflow-auto max-h-[calc(100vh-10rem)]">
                <Draw
                  isRaffling={isRaffling}
                  rafflingResponse={rafflingResponse}
                  familyPoint={familyPoint} // Draw 컴포넌트에 familyPoint
                  resetTrigger={isOpen || selectedTab}
                />
              </div>
            )}
            <div className="mt-auto w-full">
              <div className="flex justify-end items-center">
                {/* segment1일 때 "열매 배치" 버튼 표시 */}
                {selectedTab === 'segment1' && (
                  <span className="mr-auto">
                    <SmallButton text="열매 배치" url={`/tree/${family.id}/decorate`} />
                  </span>
                )}

                {/* segment3일 때 "상자 열기" 버튼 표시 */}
                {selectedTab === 'segment3' && (
                  <span className="mr-auto">
                    <button
                      className={`px-4 py-2 rounded-md m-4 transition-all duration-150 ${
                        disableOpenBoxButton
                          ? 'bg-gray-300 text-white opacity-50 cursor-not-allowed pointer-events-none'
                          : 'bg-gray-300 hover:bg-gray-400 text-black'
                      }`}
                      onClick={!disableOpenBoxButton ? handleRaffle : undefined}
                      disabled={disableOpenBoxButton} // HTML의 기본 disabled 속성을 사용하여 접근성 향상
                    >
                      상자 열기
                    </button>
                  </span>
                )}

                {/* 공통 "닫기" 버튼 */}
                <span onClick={closeModal}>
                  <SmallButton text="닫기" />
                </span>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default BoardModal;
