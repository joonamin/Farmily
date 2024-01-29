import { useState, useEffect } from 'react';

import ArticleDetail from '../components/common/ArticleDetail';
import Comment from '../components/common/Comment';
import axios from '../api/axios.jsx';

export default function DailyDetailPage() {
  const [record, setRecord] = useState({
    title: '제목',
    content: '내용',
  });
  axios
    .get('/record/1')
    .then((response) => {
      console.log(response.data);
      setRecord(response.data);
    })
    .catch((error) => {
      console.log(error);
    });

  return (
    <div className="overflow-y-auto max-h-full p-10">
      <ArticleDetail title={record.title} content={record.content} />
      <Comment />
    </div>
  );
}
