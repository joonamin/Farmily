import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import EventCard from '../components/common/EventCard.jsx';
import axios from '../api/axios.jsx';

export default function EventDetailPage() {
  const { recordId } = useParams();
  const URL = `/record/${recordId}`;

  const [record, setRecord] = useState({
    title: '',
    content: '',
    author: { nickname: '' },
    createdAt: '',
    imageCards: [
      { image: { location: '', originalFileName: '' }, description: '' },
    ],
    comments: [],
  });

  useEffect(() => {
    axios
      .get(URL)
      .then((response) => {
        setRecord(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

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
      <div className="flex h-3/5 snap-x overflow-x-scroll px-6 justify-center">
        <div className="h-full w-full flex m-auto">
          {record.imageCards.map((item, index) => (
            <EventCard key={index} {...item} />
          ))}
        </div>
      </div>
      <div className="">
        <p>댓글공간</p>
      </div>
    </div>
  );
}
