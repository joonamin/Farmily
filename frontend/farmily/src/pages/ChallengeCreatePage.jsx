import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CreateDetail from '../components/common/CreateDetail.jsx';
import SmallButton from '../components/button/SmallButton.jsx';
import axios from '../api/axios.jsx';

export default function ChallengeCreatePage() {
  const [errorMessage, setErrorMessage] = useState('');
  const today = new Date().toISOString().split('T')[0];
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    // 로그인 후 sprintId 받아오면 수정하기
    sprintId: 1,
    title: '',
    content: '',
    dateRange: {
      startDate: today,
      endDate: '',
    },
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleDateChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      dateRange: {
        ...prevFormData.dateRange,
        [name]: value,
      },
    }));
  };

  const handleClick = () => {
    if (!formData.title) {
      setErrorMessage('제목을 입력해 주세요.');
      return;
    }
    if (!formData.content) {
      setErrorMessage('내용을 입력해 주세요.');
      return;
    }
    if (!formData.dateRange.startDate || !formData.dateRange.endDate) {
      setErrorMessage('챌린지 기간을 입력해 주세요.');
      return;
    }
    // 로그인 후 유저 정보 같이 보내기
    axios
      .post('/record/challenge', formData)
      .then((response) => {
        navigate('/family/record');
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <h1>챌린지 글쓰기</h1>
      <div className="h-1/2">
        <CreateDetail
          title={formData.title}
          content={formData.content}
          onInputChange={handleChange}
        />
      </div>
      <div className="h-1/4">
        <label htmlFor="startDate">시작 날짜 : </label>
        <input
          type="date"
          id="startDate"
          name="startDate"
          className="mr-32 border border-stone-700 rounded"
          value={formData.dateRange.startDate}
          onChange={handleDateChange}
          min={today}
        />
        <label htmlFor="endDate">종료 날짜 : </label>
        <input
          type="date"
          id="endDate"
          name="endDate"
          className="border border-stone-700 rounded"
          value={formData.dateRange.endDate}
          onChange={handleDateChange}
          min={formData.dateRange.startDate}
        />
      </div>
      <div className="h-1/4">
        <span onClick={handleClick}>
          <SmallButton text="글쓰기" />
        </span>
        <p className="text-red-400">{errorMessage}</p>
      </div>
    </>
  );
}
