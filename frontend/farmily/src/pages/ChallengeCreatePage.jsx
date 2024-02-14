import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import CreateDetail from '../components/common/CreateDetail.jsx';
import SmallButton from '../components/button/SmallButton.jsx';
import axios from '../api/axios.jsx';

export default function ChallengeCreatePage() {
  const [errorMessage, setErrorMessage] = useState('');
  const day = new Date();
  const today = new Date(
    day.toLocaleString('en-US', { timeZone: 'Asia/Seoul' })
  )
    .toISOString()
    .split('T')[0];
  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);

  const [formData, setFormData] = useState({
    sprintId: family.mainSprint.sprintId,
    familyId: family.id,
    title: '',
    content: '',
    dateRange: {
      startDate: today, // 시작 날짜를 오늘로 설정
      endDate: '',
    },
  });

  useEffect(() => {
    // 종료 날짜의 기본값을 시작 날짜의 다음 날로 설정
    const startDate = new Date(formData.dateRange.startDate);
    startDate.setDate(startDate.getDate() + 1);
    const endDateStr = startDate.toISOString().split('T')[0];

    setFormData((prevFormData) => ({
      ...prevFormData,
      dateRange: {
        ...prevFormData.dateRange,
        endDate: endDateStr,
      },
    }));
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleDateChange = (e) => {
    const { name, value } = e.target;
    if (name === 'endDate') {
      setFormData((prevFormData) => ({
        ...prevFormData,
        dateRange: {
          ...prevFormData.dateRange,
          [name]: value,
        },
      }));
    }
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
    if (!formData.dateRange.endDate) {
      setErrorMessage('챌린지 기간을 입력해 주세요.');
      return;
    }

    axios
      .post('/record/challenge', formData)
      .then((response) => {
        navigate(`/family/record/${family.mainSprint.sprintId}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <h1 className="text-2xl font-bold">챌린지 글쓰기</h1>
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
          disabled={true} // 시작 날짜를 변경할 수 없도록 설정
        />
        <label htmlFor="endDate">종료 날짜 : </label>
        <input
          type="date"
          id="endDate"
          name="endDate"
          className="border border-stone-700 rounded"
          value={formData.dateRange.endDate}
          onChange={handleDateChange}
          min={formData.dateRange.endDate}
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
