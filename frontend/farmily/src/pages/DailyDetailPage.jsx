import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ArticleDetail from '../components/common/ArticleDetail';
import Comment from '../components/common/Comment';
import axios from '../api/axios.jsx';

export default function DailyDetailPage() {
  const { recordId } = useParams();
  const URL = `/record/${recordId}`;

  const [record, setRecord] = useState({
    title: '',
    content: '',
    createdAt: '',
    author: { nickname: '' },
  });

  useEffect(() => {
    axios
      .get(URL)
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
