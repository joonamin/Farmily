import React, { useState, useEffect, useRef } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import challenge from '../../assets/images/challenge_modal.png'; // 이미지를 불러오기 위한 경로 설정
import axios from '../../api/axios.jsx';
import SmallButton from '../button/SmallButton';

function getDayOfWeek(date) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  return days[date.getDay()];
}

function isToday(date) {
  const today = new Date();
  return (
    date.getDate() === today.getDate() &&
    date.getMonth() === today.getMonth() &&
    date.getFullYear() === today.getFullYear()
  );
}

function getDayStyle(date) {
  const dayOfWeek = date.getDay();
  if (isToday(date)) {
    return 'cursor-pointer'; // 오늘인 경우
  } else if (dayOfWeek === 0) {
    return 'text-red-500'; // 일요일인 경우
  } else if (dayOfWeek === 6) {
    return 'text-blue-500'; // 토요일인 경우
  } else {
    return 'opacity-60 cursor-not-allowed'; // 다른 날은 불투명하고 클릭 비활성화
  }
}

export default function ChallengeCalendar({ startDate, endDate, recordId, progresses, handleMark, handleRewardClick }) {
  const family = useSelector((state) => state.family.value);
  const [currentWeekIndex, setCurrentWeekIndex] = useState(0);
  const [weekDates, setWeekDates] = useState([]);
  const [images, setImages] = useState({});
  const [selectedDate, setSelectedDate] = useState(null);
  const [showConfirmationModal, setShowConfirmationModal] = useState(false);
  const modalRef = useRef();
  const navigator = useNavigate();

  function generateWeekDates(weekIndex) {
    const startOfWeek = new Date(startDate);
    startOfWeek.setDate(startOfWeek.getDate() + weekIndex * 7);
    startOfWeek.setDate(startOfWeek.getDate() - startOfWeek.getDay());

    const week = [];
    for (let i = 0; i < 7; i++) {
      const date = new Date(startOfWeek);
      date.setDate(date.getDate() + i);
      week.push(date);
    }

    return week;
  }

  useEffect(() => {
    const newWeekDates = generateWeekDates(currentWeekIndex);
    setWeekDates(newWeekDates);

    if (Array.isArray(progresses)) {
      const uniqueProgresses = new Set(progresses.map((date) => new Date(date).toISOString().slice(0, 10)));
      const newImages = {};
      uniqueProgresses.forEach((day) => {
        newImages[day] = challenge; // 이미지 할당
      });
      setImages(newImages);
    }
  }, [currentWeekIndex, progresses]);

  const moveToPreviousWeek = () => {
    setCurrentWeekIndex(currentWeekIndex - 1);
  };

  const moveToNextWeek = () => {
    setCurrentWeekIndex(currentWeekIndex + 1);
  };

  const handleImageClick = (date) => {
    const today = new Date().toISOString().slice(0, 10);
    const clickedDate = date.toISOString().slice(0, 10);

    if (clickedDate === today) {
      if (images[clickedDate]) {
        setSelectedDate(clickedDate);
        setShowConfirmationModal(true);
      } else {
        setImages({ ...images, [clickedDate]: challenge });
        axios
          .post('/record/challenge/mark', {
            challengeId: recordId,
            date: clickedDate,
          })
          .then((response) => {
            handleMark();
            // 성공적으로 마크되었을 때의 처리
          })
          .catch((error) => {
            console.log(error);
          });
      }
    } else {
      console.log('오늘 날짜만 선택 가능합니다.');
    }
  };

  const handleDeleteClick = () => {
    axios
      .delete(`record/${recordId}`)
      .then((response) => {
        navigator(`/family/record/${family.mainSprint.sprintId}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  useEffect(() => {
    const newWeekDates = generateWeekDates(currentWeekIndex);
    setWeekDates(newWeekDates);
  }, [currentWeekIndex]);

  const weekDisplay = (
    <div className="flex items-center justify-between px-4 py-2">
      <button onClick={moveToPreviousWeek}>&lt;</button>
      <div className="flex justify-around flex-grow">
        {weekDates.map((date, index) => (
          <div
            key={index}
            className={`text-center ${getDayStyle(date)}`}
            onClick={() => isToday(date) && handleImageClick(date)}
          >
            <div>{`${date.getMonth() + 1}/${date.getDate()}(${getDayOfWeek(date)})`}</div>
            <div className="w-20 h-20 mt-2 border">
              {images[date.toISOString().slice(0, 10)] ? (
                <img src={images[date.toISOString().slice(0, 10)]} alt="Selected" className="w-full h-full" />
              ) : (
                <div className="flex items-center justify-center w-full h-full"></div>
              )}
            </div>
          </div>
        ))}
      </div>
      <button onClick={moveToNextWeek}>&gt;</button>
    </div>
  );

  return (
    <div>
      <div className="flex items-center justify-center h-1/2 w-full">
        <div className="relative max-w-2xl mx-auto bg-white rounded-md shadow-lg">
          {weekDisplay}
          {/* Confirmation modal and other UI elements as needed */}
        </div>
      </div>
      <div className="flex justify-center mt-4 space-x-60 ">
        <span onClick={handleDeleteClick}>
          <SmallButton text="포기하기" />
        </span>
        <span onClick={handleRewardClick}>
          <SmallButton text="열매받기" />
        </span>
      </div>
    </div>
  );
}
