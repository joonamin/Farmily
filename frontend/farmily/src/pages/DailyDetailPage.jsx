import { useState, useEffect } from 'react';

import ArticleDetail from '../components/common/ArticleDetail';
import Comment from '../components/common/Comment';
import axios from '../api/axios.jsx';

export default function DailyDetailPage() {
  const [record, setRecord] = useState({
    title: '',
    content: '',
    createdAt: '',
    author: { nickname: '' },
  });
  useEffect(() => {
    axios
      .get('/record/1')
      .then((response) => {
        setRecord(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="overflow-y-auto max-h-full p-10">
      <ArticleDetail {...record} />
      <Comment />
    </div>
  );
}
