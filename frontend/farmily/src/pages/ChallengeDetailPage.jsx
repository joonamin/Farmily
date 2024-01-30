import { useState, useEffect } from 'react';

import Comment from '../components/common/Comment.jsx';
import ChallengeCalendar from '../components/common/ChallengeCalendar.jsx';
import ArticleDetail from '../components/common/ArticleDetail.jsx';
import axios from '../api/axios.jsx';

export default function ChallengeDetailPage() {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());

  const [record, setRecord] = useState({
    title: '',
    content: '',
    createdAt: '',
    author: { nickname: '' },
    dateRange: { startDate: '', endDate: '' },
  });
  useEffect(() => {
    axios
      .get('/record/17')
      .then((response) => {
        console.log(response.data);
        setRecord(response.data);
        setStartDate(new Date(response.data.dateRange.startDate.slice(0, 10)));
        setEndDate(new Date(response.data.dateRange.endDate.slice(0, 10)));
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="overflow-y-auto max-h-full p-10">
      <ArticleDetail {...record} />
      <div>
        <span className="mr-24">
          시작 기간 :{record.dateRange.startDate.slice(0, 10)}
        </span>
        <span>종료 기간 : {record.dateRange.endDate.slice(0, 10)}</span>
      </div>
      <div>{startDate.toLocaleDateString()}</div>
      <ChallengeCalendar startDate={startDate} endDate={endDate} />
      <Comment />
    </div>
  );
}
