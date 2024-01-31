import React, { useState, useEffect, useRef } from 'react';
import challenge from '../../assets/images/challenge_modal.png';
import axios from '../../api/axios.jsx';

function getDayOfWeek(date) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  return days[date.getDay()];
}

export default function ChallengeCalendar({ startDate, endDate, recordId }) {
  const [currentWeekIndex, setCurrentWeekIndex] = useState(0);
  const [weekDates, setWeekDates] = useState([]);
  const [images, setImages] = useState({});
  const [selectedDate, setSelectedDate] = useState(null);
  const [showConfirmationModal, setShowConfirmationModal] = useState(false);
  const modalRef = useRef();

  function generateWeekDates(weekIndex) {
    const startOfWeek = new Date(startDate); // startDate로부터 시작

    // 현재 주의 첫 번째 날을 계산
    startOfWeek.setDate(startOfWeek.getDate() + weekIndex * 7);

    // 일요일로 조정
    startOfWeek.setDate(startOfWeek.getDate() - startOfWeek.getDay());

    const week = [];
    for (let i = 0; i < 7; i++) {
      const date = new Date(startOfWeek);
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
    if (images[date]) {
      setSelectedDate(date);
      setShowConfirmationModal(true);
    } else {
      setImages({ ...images, [date]: challenge });
      const day = date.toISOString().slice(0, 10);
      axios
        .post('/record/challenge/mark', {
          challengeId: recordId,
          date: day,
        })
        .then((response) => {})
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const handleConfirmDelete = () => {
    const { [selectedDate]: deletedImage, ...remainingImages } = images;
    setImages(remainingImages);
    setShowConfirmationModal(false);
  };

  const handleCancelDelete = () => {
    setShowConfirmationModal(false);
  };

  useEffect(() => {
    const newWeekDates = generateWeekDates(currentWeekIndex);
    setWeekDates(newWeekDates);
  }, [currentWeekIndex]);

  const titleBar = (
    <div className="flex flex-col items-center justify-center px-4 py-2 border-b">
      <h1 className="text-xl font-bold">Title</h1>
      <span>
        {weekDates[0]?.toLocaleDateString()} ~{' '}
        {weekDates[6]?.toLocaleDateString()}
      </span>
    </div>
  );

  const weekDisplay = (
    <div className="flex items-center justify-between px-4 py-2">
      <button onClick={moveToPreviousWeek}>&lt;</button>
      <div className="flex justify-around flex-grow">
        {weekDates.map((date, index) => (
          <div key={index} className="text-center">
            <div>
              {`${date.getMonth() + 1}/${date.getDate()}(${getDayOfWeek(
                date
              )})`}
            </div>
            <div
              className="w-20 h-20 mt-2 border cursor-pointer"
              onClick={() => handleImageClick(date)}
            >
              {images[date] ? (
                <img
                  src={images[date]}
                  alt="Selected"
                  className="w-full h-full"
                />
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

  // const confirmationModal = (
  //   <div
  //     className={`fixed inset-0 flex items-center justify-center bg-gray-600 bg-opacity-50 overflow-y-auto h-auto w-full ${
  //       showConfirmationModal ? 'block' : 'hidden'
  //     }`}
  //     style={{ zIndex: showConfirmationModal ? 9999 : -1 }}
  //   >
  //     <div
  //       className="relative max-w-md mx-auto bg-white rounded-md shadow-lg p-4"
  //       ref={modalRef}
  //     >
  //       <p>정말 취소하시겠습니까?</p>
  //       <div className="mt-4 flex justify-center">
  //         <button
  //           onClick={handleCancelDelete}
  //           className="mr-2 bg-gray-300 px-4 py-2 rounded-md"
  //         >
  //           취소
  //         </button>
  //         <button
  //           onClick={handleConfirmDelete}
  //           className="bg-red-500 px-4 py-2 rounded-md text-white"
  //         >
  //           확인
  //         </button>
  //       </div>
  //     </div>
  //   </div>
  // );

  return (
    <div className="flex items-center justify-center h-1/2 w-full">
      <div className="relative max-w-2xl mx-auto bg-white rounded-md shadow-lg">
        {titleBar}
        {weekDisplay}
        {/* {confirmationModal} */}
      </div>
    </div>
  );
}
