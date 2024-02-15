import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import EventCard from '../components/common/EventCard.jsx';
import axios from '../api/axios.jsx';
import Comment from '../components/common/Comment';

export default function EventDetailPage() {
  const { recordId } = useParams();
  const [isChange, setIsChange] = useState(true);
  const [record, setRecord] = useState({
    title: '',
    content: '',
    author: { nickname: '' },
    createdAt: '',
    imageCards: [
      { image: { location: '', originalFileName: '' }, description: '' },
    ],
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
    <div className="h-full w-full overflow-hidden p-10">
      <div className="h-1/12">
        <div className="border-4 p-2 border-black rounded-xl text-left">
          <p>{record.title}</p>
        </div>
      </div>
      <div className="flex justify-between">
        <span className="text-left">
          작성일자 : {record.createdAt.slice(0, 10)}
        </span>
        <span className="text-right">작성자 : {record.author.nickname}</span>
      </div>
      <div className="flex h-3/4 snap-x overflow-x-scroll px-6 justify-center">
        <div className="h-full w-full flex m-auto">
          {record.imageCards.map((item, index) => (
            <EventCard key={index} {...item} />
          ))}
        </div>
      </div>
      <div className="">
        <Comment
          comments={record.comments}
          recordId={recordId}
          onCommentCreate={onCommentCreate}
        />
      </div>
    </div>
  );
}
