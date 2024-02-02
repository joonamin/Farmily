import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ArticleDetail from '../components/common/ArticleDetail';
import Comment from '../components/common/Comment';
import axios from '../api/axios.jsx';

export default function DailyDetailPage() {
  const { recordId } = useParams();
  const [isChange, setIsChange] = useState(true);
  const [record, setRecord] = useState({
    title: '',
    content: '',
    createdAt: '',
    author: { nickname: '' },
    comments: [{ content: '', createdAt: '', author: { nickname: '' } }],
  });
  const onCommentCreate = () => {
    setIsChange(!isChange);
  };
  useEffect(() => {
    axios
      .get(`/record/${recordId}`)
      .then((response) => {
        setRecord(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [isChange]);

  return (
    <div className="overflow-y-auto max-h-full p-10">
      <ArticleDetail {...record} />
      <Comment
        comments={record.comments}
        recordId={recordId}
        onCommentCreate={onCommentCreate}
      />
    </div>
  );
}
