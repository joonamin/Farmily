import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import Comment from '../components/common/Comment.jsx';
import ChallengeCalendar from '../components/common/ChallengeCalendar.jsx';
import ArticleDetail from '../components/common/ArticleDetail.jsx';
import axios from '../api/axios.jsx';
import CommonModal from '../components/common/CommonModal.jsx';

export default function ChallengeDetailPage() {
  const { recordId } = useParams();
  const [isChange, setIsChange] = useState(true);
  const URL = `/record/${recordId}`;
  const family = useSelector((state) => state.family.value);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState('');
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [progresses, setProgresses] = useState([]);
  const [record, setRecord] = useState({
    title: '',
    content: '',
    createdAt: '',
    author: { nickname: '' },
    dateRange: { startDate: '', endDate: '' },
    comments: [{ content: '', createdAt: '', author: { nickname: '' } }],
  });

  const handleRewardClick = () => {
    axios
      .post(`record/${recordId}/receive-reward`, {
        familyId: family.id,
      })
      .then((response) => {
        setModalContent('챌린지 보상을 받았습니다 !');
        setIsModalOpen(true);
      })
      .catch((error) => {
        setModalContent(error.response.data);
        setIsModalOpen(true);
      });
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };
  const onCommentCreate = () => {
    setIsChange(!isChange);
  };
  useEffect(() => {
    axios
      .get(URL)
      .then((response) => {
        setRecord(response.data);
        setStartDate(new Date(response.data.dateRange.startDate.slice(0, 10)));
        setEndDate(new Date(response.data.dateRange.endDate.slice(0, 10)));
        setProgresses(
          Array.isArray(response.data.progresses)
            ? response.data.progresses
            : []
        );
      })
      .catch((error) => {
        console.log(error);
      });
  }, [isChange]);

  return (
    <div className="overflow-y-auto max-h-full p-10">
      <ArticleDetail {...record} />
      <div>
        <span className="mr-24">
          시작 기간 :{record.dateRange.startDate.slice(0, 10)}
        </span>
        <span>종료 기간 : {record.dateRange.endDate.slice(0, 10)}</span>
      </div>
      {/* <div>{startDate.toLocaleDateString()}</div> */}
      <ChallengeCalendar
        startDate={startDate}
        endDate={endDate}
        recordId={recordId}
        progresses={progresses}
        handleRewardClick={handleRewardClick}
      />
      <Comment
        comments={record.comments}
        recordId={recordId}
        onCommentCreate={onCommentCreate}
      />
      <CommonModal
        title="챌린지"
        content={modalContent}
        isOpen={isModalOpen}
        closeModal={closeModal}
      />
    </div>
  );
}
