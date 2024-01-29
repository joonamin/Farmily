import React, { useState, useEffect, useRef } from 'react';
import challenge from '../../assets/images/challenge_modal.png';
import SmallButton from '../button/SmallButton.jsx';

// 요일을 반환하는 함수
function getDayOfWeek(date) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  return days[date.getDay()];
}

export default function ChallengeModal({ isOpen, onClose }) {
  const [currentWeekIndex, setCurrentWeekIndex] = useState(0);
  const [weekDates, setWeekDates] = useState([]);
  const [images, setImages] = useState({});
  const modalRef = useRef();

  function generateWeekDates(weekIndex) {
    const now = new Date();
    now.setDate(now.getDate() + weekIndex * 7);

    const firstDayOfWeek = now.getDate() - now.getDay();
    now.setDate(firstDayOfWeek);

    const week = [];
    for (let i = 0; i < 7; i++) {
      const date = new Date(now);
      date.setDate(date.getDate() + i);
      week.push(date);
    }

    return week;
  }

  const moveToPreviousWeek = () => {
    setCurrentWeekIndex(currentWeekIndex - 1);
  };

  const moveToNextWeek = () => {
    setCurrentWeekIndex(currentWeekIndex + 1);
  };

  const handleImageClick = (date) => {
    setImages({ ...images, [date]: challenge });
  };

  useEffect(() => {
    const newWeekDates = generateWeekDates(currentWeekIndex);
    setWeekDates(newWeekDates);
  }, [currentWeekIndex]);

  useEffect(() => {
    function handleClickOutside(event) {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose();
      }
    }

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [onClose]);

  const titleBar = (
    <div className="flex flex-col items-center justify-center px-4 py-2 border-b">
      <h1 className="text-xl font-bold">Title</h1>
      <span>
        {weekDates[0]?.toLocaleDateString()} ~{' '}
        {weekDates[6]?.toLocaleDateString()}
      </span>
    </div>
  );

  const pixelButtonStyle = {
    display: 'inline-block',
    width: '30px',
    height: '30px',
    backgroundColor: '#000',
    color: '#fff', // 글자 색상 추가
    fontFamily: "'Pixel', sans-serif",
    fontSize: '15px',
    border: 'none',
    cursor: 'pointer',
    imageRendering: 'pixelated',
  };

  const weekDisplay = (
    <div className="flex items-center justify-between px-4 py-2">
      <button
        onClick={moveToPreviousWeek}
        style={{ ...pixelButtonStyle, marginTop: '30px', marginRight: '5px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}
      >
        &lt;
      </button>
      <div className="flex justify-around flex-grow">
        {weekDates.map((date, index) => (
          <div key={index} className="text-center">
            <div>
              {`${date.getMonth() + 1}/${date.getDate()}(${getDayOfWeek(
                date
              )})`}
            </div>
            <div
              className="w-20 h-20 mt-2 border cursor-pointer flex items-center justify-center text-center" // 세로 가운데 정렬을 위해 flex 사용
              onClick={() => handleImageClick(date)}
            >
              {images[date] ? (
                <img
                  src={images[date]}
                  alt="Selected"
                  className="max-w-full max-h-full mx-auto" // 이미지를 가로, 세로 중앙으로 정렬
                />
              ) : (
                <div className="w-full h-full"></div>
              )}
            </div>
          </div>
        ))}
      </div>
      <button
        onClick={moveToNextWeek}
        style={{ ...pixelButtonStyle, marginTop: '30px', marginLeft: '5px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}
      >
        &gt;
      </button>
    </div>
  );
  
  const bottomButtons = (
    <div className="flex justify-end mt-0">
      <div>
        <SmallButton text="포기하기" url="" />
      </div>
      <div className="mr-10">
        <SmallButton text="열매받기" url="" />
      </div>
    </div>
  );

  return (
    <div
      className={`fixed inset-0 flex items-center justify-center bg-gray-600 bg-opacity-50 overflow-y-auto h-auto w-full ${
        isOpen ? 'block' : 'hidden'
      }`}
      style={{ zIndex: isOpen ? 9999 : -1 }}
    >
      <div
        className="relative max-w-2xl mx-auto my-20 bg-white rounded-md shadow-lg"
        ref={modalRef}
      >
        {titleBar}
        {weekDisplay}
        {bottomButtons}
        <button onClick={onClose} className="absolute top-2 right-2">
          &times;
        </button>
      </div>
    </div>
  );
}
