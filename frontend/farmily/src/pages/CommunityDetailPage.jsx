import { useEffect, useState } from 'react';
import axios from '../api/axios.jsx';
import { useParams } from 'react-router-dom';

export default function CommunityDetailPage() {
  const [community, setCommunity] = useState({
    title: '',
    content: '',
    author: '',
    createdAt: '',
    treeImage: {
      location: '',
      originalFileName: '',
    },
  });

  const id = useParams().communityId;

  useEffect(() => {
    axios
      .get(`/community/${id}`)
      .then((res) => {
        setCommunity(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="h-full">
      <div className="h-1/6 p-10">
        <div className="border-4 p-2 border-black rounded-xl text-left flex justify-between">
          <p>{community.title}</p>
        </div>
        <div className="flex justify-between mt-1">
          <span className="text-left">
            {/* 작성일자 :작성일자 : {community.createdAt.slice(0, 10)} */}
          </span>
          <span className="text-right">작성자 : {community.author}</span>
        </div>
      </div>
      <div className="h-3/6">
        <img
          src={community.treeImage.location}
          alt=""
          className="mx-auto object-contain h-full"
        />
      </div>
      <div className="h-2/6">{community.content}</div>
    </div>
  );
}
