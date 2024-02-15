import React, { useState, useEffect, useRef } from 'react';
import { useSelector } from 'react-redux';
import axios from '../../api/axios';
import challengeImage from '../../assets/images/challenge_modal.png';
import SmallButton from '../button/SmallButton';
import CommonModal from '../../components/common/CommonModal.jsx';
function getDayOfWeek(date) {
  const days = ['일', '월', '화', '수', '목', '금', '토'];
  return days[date.getDay()];
}

export default function ChallengeModal({
  isOpen,
  onClose,
  challengeData,
  handleChange,
}) {
  const [currentWeekIndex, setCurrentWeekIndex] = useState(0);
  const [weekDates, setWeekDates] = useState([]);
  const [images, setImages] = useState({});
  const [selectedDate, setSelectedDate] = useState(null);
  const [showConfirmationModal, setShowConfirmationModal] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState('');
  const family = useSelector((state) => state.family.value);
  const modalRef = useRef();
  const handleClose = () => {
    onClose(); // 모달 닫기
    // window.location.reload(); // 페이지 새로고침
  };
  const generateWeekDates = (weekIndex) => {
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
  };

  const handleRewardClick = () => {
    axios
      .post(`record/${challengeData.id}/receive-reward`, {
        familyId: family.id,
      })
      .then((response) => {
        setModalContent('챌린지 보상을 받았습니다 !');
        setIsModalOpen(true);
        handleChange();
      })
      .catch((error) => {
        setModalContent(error.response.data);
        setIsModalOpen(true);
      });
  };
  const handleDeleteClick = () => {
    axios
      .delete(`record/${challengeData.id}`)
      .then((response) => {
        handleChange();
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    const newWeekDates = generateWeekDates(currentWeekIndex);
    setWeekDates(newWeekDates);

    const newImages = {};
    challengeData?.progresses.forEach((progressDate) => {
      const progressDateObj = new Date(progressDate);
      const progressKey = progressDateObj.toISOString().split('T')[0];
      newImages[progressKey] = challengeImage;
    });
    setImages(newImages);
  }, [currentWeekIndex, challengeData]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose();
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [onClose]);

  const handleImageClick = (date) => {
    const today = new Date().toISOString().slice(0, 10);
    const clickedDate = date.toISOString().slice(0, 10);

    if (clickedDate === today) {
      if (images[clickedDate]) {
        setSelectedDate(clickedDate);
        setShowConfirmationModal(true);
      } else {
        setImages({ ...images, [clickedDate]: challengeImage });
        axios
          .post('/record/challenge/mark', {
            challengeId: challengeData.id,
            date: clickedDate,
          })
          .then((response) => {
            handleChange();
          })
          .catch((error) => {
            console.log(error);
          });
      }
    } else {
      console.log('오늘 날짜만 선택 가능합니다.');
    }
  };

  return (
    <div
      className={`fixed inset-0 flex items-center justify-center ${
        isOpen ? 'block' : 'hidden'
      }`}
      style={{ zIndex: 9999 }}
    >
      <div className="relative bg-white rounded-lg shadow-lg" ref={modalRef}>
        <div className="flex flex-col items-center justify-center px-4 py-2 border-b">
          <h1 className="text-xl font-bold">
            {challengeData?.title || '챌린지 제목'}
          </h1>
          <span>
            {challengeData?.dateRange?.startDate &&
              new Date(
                challengeData.dateRange.startDate
              ).toLocaleDateString()}{' '}
            ~
            {challengeData?.dateRange?.endDate &&
              new Date(challengeData.dateRange.endDate).toLocaleDateString()}
          </span>
        </div>

        <div className="flex items-center justify-between px-4 py-2">
          <button onClick={() => setCurrentWeekIndex(currentWeekIndex - 1)}>
            &lt;
          </button>
          <div className="flex justify-around flex-grow">
            {weekDates.map((date, index) => {
              const dateKey = date.toISOString().split('T')[0];
              const dayOfWeek = date.getDay();
              const isToday = new Date().toDateString() === date.toDateString();
              const dateStyle = isToday ? {} : { opacity: 0.6 }; // 오늘 날짜가 아니면 투명도 50%
              const dayColor =
                dayOfWeek === 6 ? 'blue' : dayOfWeek === 0 ? 'red' : 'black'; // 토요일은 파란색, 일요일은 빨간색

              return (
                <div key={index} className="text-center" style={dateStyle}>
                  <div style={{ color: dayColor }}>
                    {`${date.getMonth() + 1}/${date.getDate()}(${getDayOfWeek(
                      date
                    )})`}
                  </div>
                  <div
                    className="w-20 h-20 mt-2 border cursor-pointer flex items-center justify-center"
                    onClick={() => handleImageClick(date)}
                  >
                    {images[dateKey] && (
                      <img
                        src={images[dateKey]}
                        alt="Selected"
                        className="max-w-full max-h-full"
                      />
                    )}
                  </div>
                </div>
              );
            })}
          </div>
          <button onClick={() => setCurrentWeekIndex(currentWeekIndex + 1)}>
            &gt;
          </button>
        </div>

        <div className="flex justify-end mt-4">
          <span onClick={handleDeleteClick}>
            <SmallButton
              text="포기하기"
              onClick={() => console.log('포기하기')}
            />
          </span>
          <span onClick={handleRewardClick}>
            <SmallButton text="열매받기" />
          </span>
        </div>

        <button onClick={handleClose} className="absolute top-2 right-2">
          &times;
        </button>
        {showConfirmationModal && <div> {/* Confirmation Modal 내용 */} </div>}
      </div>
      <CommonModal
        title="챌린지"
        content={modalContent}
        isOpen={isModalOpen}
        closeModal={closeModal}
      />
    </div>
  );
}
